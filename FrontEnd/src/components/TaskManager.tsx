import { useEffect, useState } from "react";

interface User {
  id: string;
  name: string;
  role: string;
  level: string;
  available: boolean;
}

interface Task {
  id: string;
  description: string;
  status: "Complete" | "Incomplete";
  priority: "Low" | "Critical";
  userId: string;
}

export default function TaskAssigner() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    // Obtener las tareas
    fetch("/tasks") // Cambia esto según la URL de tus tareas
      .then((res) => res.json())
      .then((data) => setTasks(data))
      .catch((err) => setError("No se pudieron cargar las tareas."))
      .finally(() => setLoading(false));

    // Obtener los usuarios disponibles
    fetch("/users/available")
      .then((res) => res.json())
      .then((data) => setUsers(data))
      .catch((err) =>
        setError("No se pudieron cargar los usuarios disponibles.")
      );
  }, []);

  const handleAssignAgent = (taskId: string) => {
    fetch(`/tasks/${taskId}/agentAssign`, {
      method: "PATCH",
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error("Error al asignar agente");
        }
        // Recargar las tareas o mostrar un mensaje de éxito
        setTasks((prevTasks) =>
          prevTasks.map((task) =>
            task.id === taskId ? { ...task, userId: "Agente asignado" } : task
          )
        );
      })
      .catch((err) => setError("No se pudo asignar el agente."));
  };

  if (loading) return <p>Cargando tareas...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div>
      <h2>Asignar Agente a Tarea</h2>
      <div>
        {tasks
          .filter((task) => task.status !== "Complete")
          .map((task) => (
            <div key={task.id} style={{ marginBottom: "10px" }}>
              <h3>{task.description}</h3>
              <p>Prioridad: {task.priority}</p>
              <p>Estado: {task.status}</p>
              <button onClick={() => handleAssignAgent(task.id)}>
                Asignar Agente
              </button>
            </div>
          ))}
      </div>
    </div>
  );
}
