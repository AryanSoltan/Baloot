import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import NavbarLogo from "./NavbarLogo";
import './Navbar.css'
import SearchbarHeader from './SearchbarHeader';

import HeaderinfoPart from "./HeaderinfoPart";
import { Link, useMatch } from 'react-router-dom';


function Header(props) {
    return (


        <nav className="header-container">


            <div className="col">
                <NavbarLogo />
            </div>

                <div className="col">
                    {useMatch('/commodities') &&
                        <SearchbarHeader />
                    }

                </div>

            {/*<div className="col">*/}
            {/*    <HeaderinfoPart />*/}

            {/*</div>*/}

                {/*<div className="col">*/}
                {/*    <NavbarLogo />*/}
                {/*</div>*/}

                <div className="col">
                    <HeaderinfoPart />

            </div>



        </nav >

    );
}

export default Header;
