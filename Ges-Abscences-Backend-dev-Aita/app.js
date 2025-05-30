const express = require('express');
const mongoose = require('mongoose');
const dotenv = require('dotenv');
const authRoutes = require('./routes/auth');
const presenceRoutes = require('./routes/presence');
const app = express();

dotenv.config();
app.use(express.json());

mongoose.connect(process.env.MONGODB_URI, { useNewUrlParser: true, useUnifiedTopology: true })
  .then(() => console.log('✅ Connexion à MongoDB réussie'))
  .catch((err) => console.error('❌ Échec de la connexion à MongoDB :', err));

app.use('/auth', authRoutes);
app.use('/presence', presenceRoutes);

module.exports = app;
