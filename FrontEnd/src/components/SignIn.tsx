// Login.tsx
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState(''); // Para el registro
  const [level, setLevel] = useState(''); // Para el registro
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const response = await fetch('/users/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, password }),
      });

      if (!response.ok) throw new Error('Credenciales inválidas');

      const user = await response.json();

      // Guardar en localStorage sirve para mantener la sesión activa
      localStorage.setItem('user', JSON.stringify(user));

      // Redirigir según el rol
      if (user.role === 'Programmer') {
        navigate('/dashboard');
      } else if (user.role === 'Leader') {
        navigate('/manager_dashboard');
      } else {
        alert('Rol no reconocido');
      }

    } catch (error) {
      console.error(error);
      alert('Error al iniciar sesión');
    }
  };

  //Manejo del signUp
  const handleSignUp = async () => {
    try {
      const response = await fetch('/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, password, role, level, available: true }),
      });

      if (!response.ok) throw new Error('Error al registrarse');

      alert('Registro exitoso! Ahora puedes iniciar sesión.');
    } catch (error) {
      console.error(error);
      alert('Error al registrarse');
    }
  };

  return (
    <div>
    <div>
      <h2>Iniciar sesión</h2>
      <input type="text" placeholder="Nombre" value={name} onChange={e => setName(e.target.value)} />
      <input type="password" placeholder="Contraseña" value={password} onChange={e => setPassword(e.target.value)} />
      <button onClick={handleLogin}>Entrar</button>
    </div>
    <div>
      <h2>Registrarse</h2>
      <input type="text" placeholder="Nombre" value={name} onChange={e => setName(e.target.value)} />
      <input type="password" placeholder="Contraseña" value={password} onChange={e => setPassword(e.target.value)} />
      {/* Lista desplegable para el rol entre Leader y Programmer */}
      <select value={role} onChange={e => setRole(e.target.value)}>
        <option value="">Selecciona un rol</option>
        <option value="Leader">Leader</option>
        <option value="Programmer">Programmer</option>
      </select>
      {/* Lista desplegable para el rol entre Junior y Senior */}
      <select value={level} onChange={e => setLevel(e.target.value)}>
        <option value="">Selecciona un nivel</option>
        <option value="Junior">Junior</option>
        <option value="Senior">Senior</option>
      </select>
      <button onClick={handleSignUp}>Registrarse</button>
    </div>
    </div>
  );
};

export default Login;
