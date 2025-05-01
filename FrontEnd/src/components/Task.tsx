import { useState } from "react";

interface TicketProps {
  status: "Complete" | "Incomplete";
  priority: "Low" | "Critical";
  description: string;
  userId: string;
}

export default function Ticket({
  status,
  priority,
  description,
  userId,
}: TicketProps) {
  const [ticketStatus, setTicketStatus] = useState(status);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const statusColors: Record<typeof status, React.CSSProperties> = {
    Complete: {
      backgroundColor: "#bbf7d0", // light green
      color: "#166534", // dark green
      padding: "4px 10px",
      borderRadius: "12px",
      fontSize: "14px",
    },
    Incomplete: {
      backgroundColor: "#e5e7eb", // light gray
      color: "#374151", // dark gray
      padding: "4px 10px",
      borderRadius: "12px",
      fontSize: "14px",
    },
  };

  const priorityColors: Record<typeof priority, React.CSSProperties> = {
    Low: { color: "#16a34a", fontSize: "14px" }, // green
    Critical: { color: "#dc2626", fontWeight: "bold", fontSize: "14px" }, // red
  };

  return (
    <div
      style={{
        maxWidth: "500px",
        margin: "20px auto",
        padding: "20px",
        borderRadius: "12px",
        backgroundColor: "#f5f5f5",
        boxShadow: "0 2px 10px rgba(0,0,0,0.1)",
        display: "flex",
        alignItems: "center",
        gap: "16px",
      }}
    >
      <div style={{ flex: 1 }}>
        <p
          style={{
            color: "black",
            fontSize: "18px",
            fontWeight: "500",
            marginBottom: "8px",
          }}
        >
          {description}
        </p>

        <div style={{ display: "flex", gap: "12px", marginBottom: "8px" }}>
          <span style={statusColors[ticketStatus]}>{ticketStatus}</span>
          <span style={priorityColors[priority]}>{priority} Priority</span>
        </div>

        <button
          onClick={() => setIsModalOpen(true)}
          style={{
            backgroundColor: "#64548f",
            borderRadius: "20px",
            padding: "8px 16px",
            fontSize: "14px",
            color: "white",
            border: "none",
            cursor: "pointer",
          }}
        >
          Details
        </button>

        {isModalOpen && (
          <div
            style={{
              color: "black",
              marginTop: "16px",
              padding: "12px",
              backgroundColor: "#fff",
              border: "1px solid #ccc",
              borderRadius: "8px",
              fontSize: "14px",
            }}
          >
            <p>
              <strong>User ID:</strong> {userId}
            </p>
            <p>
              <strong>Status:</strong> {ticketStatus}
            </p>
            <p>
              <strong>Priority:</strong> {priority}
            </p>
            <button
              onClick={() => setIsModalOpen(false)}
              style={{
                marginTop: "8px",
                backgroundColor: "transparent",
                color: "#007bff",
                border: "none",
                cursor: "pointer",
                textDecoration: "underline",
              }}
            >
              Close
            </button>
          </div>
        )}
      </div>
    </div>
  );
}
