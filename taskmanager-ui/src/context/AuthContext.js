import { createContext, useContext, useState, useEffect } from "react";
import { getCurrentUser, logoutUser } from "../services/authService";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

  const [currentUser, setCurrentUser] = useState(null);
  const [loading, setLoading] = useState(true);
  

  // Load user ONCE when app starts
  useEffect(() => {
    loadUser();
  }, []);

  const loadUser = async () => {
    console.log("Loading current user");
    try {
      const data = await getCurrentUser();
      console.log("CURRENT USER DATA", data);

      setCurrentUser(data);
      
    } catch (err) {
      setCurrentUser(null);
    } finally {
      setLoading(false);
    }
    
  };

  const logout = async () => {
    await logoutUser();
    setCurrentUser(null);
  };

  return (
    <AuthContext.Provider value={{
      currentUser,
      setCurrentUser,
      logout,
      loading,
      reloadUser: loadUser
    }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuthContext = () =>
  useContext(AuthContext);