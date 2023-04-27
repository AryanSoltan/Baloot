import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import './Navbar.css';

function HeaderInfoPart() {

    var isLoggedIn = localStorage.getItem('userLoggedIn');
    var userId = localStorage.getItem('userId');
    console.log("stat");
    console.log(isLoggedIn);

    //
    // const handleSignup = async () => {
    //
    // };
    //
    // const handleLogin = async () => {
    //
    // };


    if (isLoggedIn === "false") {

        return (
            <div></div>

        );
    } else if (isLoggedIn === "true") {

        return (
            <>
            <div className="mr-auto logged-in-state align-items-center">

                        <a href="#">
                            <span>{userId}</span>
                        </a>

                        <a href="#">
                            <button type="button" className="btn btn-default count-of-bought-items">
                                Card
                                <span className="badge">3</span>
                            </button>
                        </a>

            </div>
            </>
        );
    }
}

export default HeaderInfoPart;