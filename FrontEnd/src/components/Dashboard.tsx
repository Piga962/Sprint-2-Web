import { useEffect, useState } from "react";
import Ticket from "../components/Task";

interface Task {
  id: string;
  description: string;
  status: "Complete" | "Incomplete";
  priority: "Low" | "Critical";
  userId: string;
}

const Dashboard = () => {
  const [tickets, setTickets] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);
  const userId = "RNuPnSEpgwlqPitgOkjW";

  const [error, setError] = useState("");

  useEffect(() => {
    fetch(`/tasks/user/${userId}`)
      .then((res) => {
        if (!res.ok) throw new Error("Error al obtener tickets");
        return res.json();
      })
      .then((data) => setTickets(data))
      .catch((error) => {
        console.error(error);
        setError("No se pudieron cargar los tickets.");
      })
      .finally(() => setLoading(false));
  }, [userId]);

  if (loading) return <p>Cargando tickets...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div style={{ padding: "20px" }}>
      <h1 style={{ marginBottom: "20px" }}>Tus Tickets</h1>
      {tickets.length === 0 ? (
        <p>No hay tickets asignados.</p>
      ) : (
        tickets.map((ticket) => (
          <Ticket
            key={ticket.id}
            status={ticket.status}
            priority={ticket.priority}
            description={ticket.description}
            userId={ticket.userId}
          />
        ))
      )}
    </div>
  );
};

export default Dashboard;
