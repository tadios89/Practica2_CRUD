
const express = require('express')
const router = express.Router()
const bcrypt = require('bcryptjs')
const jwt = require('jsonwebtoken')

const Usuario = require('../models/Usuario')

// REGISTRO
console.log(req.body)

router.post('/register', async (req, res) => {
  try {
    const { nombre, correo, password, rol } = req.body

    const existe = await Usuario.findOne({ correo })
    if (existe) return res.status(400).json({ msg: 'Correo ya registrado' })

    const hashedPass = await bcrypt.hash(password, 10)
    const nuevoUsuario = new Usuario({ nombre, correo, password: hashedPass, rol })
    await nuevoUsuario.save()

    res.status(201).json({ msg: 'Usuario registrado con éxito' })
  } catch (error) {
    res.status(500).json({ msg: 'Error en el servidor', error })
  }
})

// LOGIN
router.post('/login', async (req, res) => {
  try {
    const { correo, password } = req.body

    const usuario = await Usuario.findOne({ correo })
    if (!usuario) return res.status(404).json({ msg: 'Usuario no encontrado' })

    const valid = await bcrypt.compare(password, usuario.password)
    if (!valid) return res.status(401).json({ msg: 'Contraseña incorrecta' })

    const token = jwt.sign(
      { id: usuario._id, rol: usuario.rol },
      process.env.JWT_SECRET,
      { expiresIn: '4h' }
    )

    res.json({ token, usuario })
  } catch (error) {
    res.status(500).json({ msg: 'Error al iniciar sesión', error })
  }
})

module.exports = router
