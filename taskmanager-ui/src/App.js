import { Routes, Route } from "react-router-dom";
import MainLayout from "./layout/MainLayout";
import Dashboard from "./pages/Dashboard";
import Tasks from "./pages/Tasks";
import Settings from "./pages/Settings";

function App() {
  return (
    <MainLayout>
      <Routes>
        <Route path="/Dashboard" element={<Dashboard />} />
        <Route path="/tasks" element={<Tasks />} />
        <Route path="/settings" element={<Settings />} />
      </Routes>
    </MainLayout>
  );
}

export default App;