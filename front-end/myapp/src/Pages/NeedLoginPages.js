import { Navigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from 'axios';
import { Outlet, useLocation } from "react-router-dom";

export default function NeedLoginPages() {
  const tokenUser = localStorage.getItem('token');
    const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
        async function fetchData() {
          try {
            const response = await axios.post("/authorized", { token: tokenUser });

            if(response.data.content === "not")
            {
                window.location.href = "http://localhost:3000/";
            }
            else
            {
                  setIsAuthenticated(true);
//                window.location.href = "http://localhost:3000/commodities";
                  return;
            }
          } catch (e) {
            console.error(e);
          }
        }
        fetchData();
    }, []);

    if(isAuthenticated === true)
    {
        return <Outlet />;
    }

//
//    fetchData();
//
//  console.log(temp);
//  if (!temp) {
//    return <Navigate to="/" replace />;
//  }
//
//  return <Outlet />;
}