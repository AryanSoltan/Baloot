import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import NavbarLogo from "./NavbarLogo";
import './Navbar.css'
import SearchbarHeader from './SearchbarHeader';

import HeaderinfoPart from "./HeaderinfoPart";


function Header(props) {
    return (


        <nav className="row Container">

            <div className="col-1">
                <NavbarLogo />
            </div>
            {props.hasSearch == "1" &&
                <div className="col-1">
                    <SearchbarHeader />
                </div>
            }
            <div className="col-1">
                <HeaderinfoPart />
            </div>


        </nav >

    );
}

export default Header;
