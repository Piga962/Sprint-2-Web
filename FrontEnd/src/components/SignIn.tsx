import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function SignIn() {
  const navigate = useNavigate();
  return (
    <div>
      <h1>Login</h1>
      <input type="email" />
      <input type="password" />
      <button>Login</button>
    </div>
  );
}
