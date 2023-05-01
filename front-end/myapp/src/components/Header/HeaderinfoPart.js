import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import './Navbar.css';
import logo from "../../assets/images/ballot.png";
import './NavbarLogo.css'

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
                {/*<div className="mr-auto">*/}
                {/*    <ul className="navbar-nav logged-in-state align-items-center">*/}
                {/*        <li className="nav-item">*/}
                {/*            <a href="#">*/}
                {/*                <span className="header-username-container">#{userId}</span>*/}
                {/*            </a>*/}
                {/*        </li>*/}
                {/*        <li className="nav-item">*/}
                {/*            <a href="#">*/}
                {/*                <button type="button" className="btn btn-default count-of-bought-items">*/}
                {/*                    Card*/}
                {/*                    <span className="badge">3</span>*/}


                {/*                </button>*/}
                {/*            </a>*/}
                {/*        </li>*/}

                {/*    </ul>*/}
                {/*</div>*/}
                <div className="header-info-part">
                    <div className="row">
                        <div className="col header-username-container">
                            #{userId}
                        </div>
                        <div className="col header-btn-container">

                            <button type="button" className="btn btn-default count-of-bought-items">Card
                                                    <span className="badge">3</span>
                            </button>

                        </div>
                    </div>
                </div>
            </>
        );
    }
}

export default HeaderInfoPart;