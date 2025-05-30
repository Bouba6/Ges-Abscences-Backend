const jwt = require('jsonwebtoken');

exports.login = (req, res) => {
  const { email, password } = req.body;

  // Pour démo seulement
  if (email === 'vigile@ism.sn' && password === '123456') {
    const token = jwt.sign({ email }, process.env.JWT_SECRET, { expiresIn: '1h' });
    return res.status(200).json({ token });
  }

  res.status(401).json({ message: 'Identifiants invalides' });
};
