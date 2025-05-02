import { useEffect, useState } from "react";
import Ticket from "./Task";
import AvailableUsers from "./AvailableWorkers";
import TaskAssigner from "./TaskManager";

interface Task {
  id: string;
  description: string;
  status: "Complete" | "Incomplete";
  priority: "Low" | "Critical";
  userId: string;
}

export default function ManagerDashboard() {
  const [tickets, setTickets] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const user = JSON.parse(localStorage.getItem("user") ?? "null");
  const userId = user?.id;


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

  const handleStatusChange = (
    id: string,
    newStatus: "Complete" | "Incomplete"
  ) => {
    // find the Ticket we want to change its status
    const ticketToUpdate = tickets.find((t) => t.id === id);

    if (!ticketToUpdate) {
      console.error("Ticket no encontrado");
      return;
    }
    const updatedTicket = {
      ...ticketToUpdate,
      status: newStatus, // Update the status
    };

    // Fetch the PUT endpoint to change the status
    fetch(`/tasks/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(updatedTicket), // Send the whole thing
    })
      .then((res) => {
        if (!res.ok) throw new Error("Error al actualizar estado");
        // Update the local state
        setTickets((prev) =>
          prev.map((t) => (t.id === id ? { ...t, status: newStatus } : t))
        );
      })
      .catch((error) => {
        console.error(error);
        alert("Error al actualizar el estado del ticket.");
      });
  };

  if (loading) return <p>Cargando tickets...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div style={{ padding: "20px" }}>
      {/* Agent */}
      <TaskAssigner />

      {/* Available Users */}
      <AvailableUsers />

      {/* Tickets */}
      <h1 style={{ marginBottom: "20px" }}>Tus Tickets</h1>
      {tickets.length === 0 ? (
        <p>No hay tickets asignados.</p>
      ) : (
        tickets.map((ticket) => (
          <Ticket
            key={ticket.id}
            id={ticket.id}
            status={ticket.status}
            priority={ticket.priority}
            description={ticket.description}
            userId={ticket.userId}
            onStatusChange={handleStatusChange}
          />
        ))
      )}
    </div>
  );
}
