import { useAuthContext } from "../context/AuthContext";
import {useAuth} from "../hooks/useAuth" ;
import { Navigate } from "react-router-dom";

function ProtectedRoute({ children }) {

  const { currentUser, loading } = useAuthContext();
  // wait until auth check finishes
if(loading){

  return <div>Loading...</div>;

}
// not logged in
if(!currentUser){

  return <Navigate to="/login" />;

}

// logged in

  

  return children;
}

export default ProtectedRoute;