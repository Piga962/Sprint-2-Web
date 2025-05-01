import { useEffect, useState } from "react";

interface User {
  id: string;
  name: string;
  password: string;
  role: string;
  level: string;
  available: boolean;
}

const AvailableUsers = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string>("");

  useEffect(() => {
    fetch("users/available")
      .then((res) => {
        if (!res.ok) {
          throw new Error("Error al obtener usuarios");
        }
        return res.json();
      })
      .then((data) => {
        // Filtrar solo los usuarios disponibles
        const availableUsers = data.filter((user: User) => user.available);
        setUsers(availableUsers);
      })
      .catch((error) => {
        setError(error.message);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Cargando usuarios...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div style={{ padding: "20px" }}>
      <h1>Usuarios Disponibles</h1>
      {users.length === 0 ? (
        <p>No hay usuarios disponibles.</p>
      ) : (
        <ul>
          {users.map((user) => (
            <li key={user.id}>
              <strong>{user.name}</strong> ({user.role} - {user.level})
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default AvailableUsers;
