import { Navigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from 'axios';
import { Outlet, useLocation } from "react-router-dom";

export default function NeedLoginPages() {
  const [isAuthorized, setIsAuthorized] = useState(false);
  const tokenUser = localStorage.getItem('token');

  var temp = false;

    async function fetchData() {
    console.log("HUUUUUUUU")
      try {
        const response = await axios.post("/authorized", { token: tokenUser });
        if(response.data.content === "ok")
        {
            console.log("WHYYYYYYYYY AAAAAA");
            setIsAuthorized(true);
            temp = true;
        }
        console.log(temp);
        console.log("HELOOOOOOOOOo");
        console.log(isAuthorized);
        console.log(response);
      } catch (e) {
        console.error(e);
      }
    }
  console.log("NAAAAAAAA");

    fetchData();

  console.log(temp);
  if (!temp) {
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
}