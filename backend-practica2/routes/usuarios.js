const express = require('express');
const router = express.Router();
const Usuario = require('../models/Usuario');

// Obtener todos los usuarios
router.get('/', async (req, res) => {
  try {
    const usuarios = await Usuario.find();
    res.json(usuarios);
  } catch (error) {
    res.status(500).json({ msg: 'Error al obtener usuarios', error });
  }
});

// Actualizar usuario
router.put('/:id', async (req, res) => {
  try {
    const { nombre, correo, rol } = req.body;
    const usuarioActualizado = await Usuario.findByIdAndUpdate(
      req.params.id,
      { nombre, correo, rol },
      { new: true }
    );
    res.json(usuarioActualizado);
  } catch (error) {
    res.status(500).json({ msg: 'Error al actualizar', error });
  }
});

// Eliminar usuario
router.delete('/:id', async (req, res) => {
  try {
    await Usuario.findByIdAndDelete(req.params.id);
    res.json({ msg: 'Usuario eliminado' });
  } catch (error) {
    res.status(500).json({ msg: 'Error al eliminar', error });
  }
});

module.exports = router;
