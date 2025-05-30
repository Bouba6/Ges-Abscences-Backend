const mongoose = require('mongoose');

const EtudiantSchema = new mongoose.Schema({
  matricule: { type: String, required: true, unique: true },
  nom: { type: String, required: true }
});

module.exports = mongoose.model('Etudiant', EtudiantSchema);
