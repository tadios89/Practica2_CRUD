require('dotenv').config()
const express = require('express')
const mongoose = require('mongoose')
const cors = require('cors')

const app = express()

// Middlewares
app.use(cors())
app.use(express.json())

// Rutas
app.use('/api', require('./routes/auth'))
app.use('/api/usuarios', require('./routes/usuarios')); // 👈 esta línea es clave

// Conexión a MongoDB
mongoose.connect(process.env.MONGO_URI)
  .then(() => {
    console.log('✅ Conectado a MongoDB Atlas')
    app.listen(process.env.PORT, () =>
      console.log(`🚀 Servidor corriendo en http://localhost:${process.env.PORT}`)
    )
  })
  .catch(err => console.error('❌ Error al conectar a MongoDB:', err))

