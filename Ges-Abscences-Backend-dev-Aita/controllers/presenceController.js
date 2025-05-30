const Etudiant = require('../models/Etudiant');
const Presence = require('../models/Presence');

exports.enregistrerPresence = async (req, res) => {
  const { matricule } = req.body;
  try {
    const etudiant = await Etudiant.findOne({ matricule });
    if (!etudiant) return res.status(404).json({ message: 'Étudiant non trouvé' });

    const presence = new Presence({ etudiantId: etudiant._id });
    await presence.save();

    res.status(201).json({ message: 'Présence enregistrée' });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};

exports.historique = async (req, res) => {
  try {
    const presences = await Presence.find().populate('etudiantId', 'nom').sort({ heure: -1 });
    const result = presences.map(p => ({
      nom: p.etudiantId.nom,
      heure: new Date(p.heure).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    }));
    res.status(200).json(result);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
};
