const mongoose = require('mongoose');

const PresenceSchema = new mongoose.Schema({
  etudiantId: { type: mongoose.Schema.Types.ObjectId, ref: 'Etudiant', required: true },
  heure: { type: Date, default: Date.now }
});

module.exports = mongoose.model('Presence', PresenceSchema);
