const express = require('express');
const router = express.Router();
const presenceController = require('../controllers/presenceController');
const auth = require('../middleware/auth');

router.post('/', auth, presenceController.enregistrerPresence);
router.get('/historique', auth, presenceController.historique);

module.exports = router;
