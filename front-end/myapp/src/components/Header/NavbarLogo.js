import React from "react";
import './NavbarLogo.css'
import logo from '../../assets/images/ballot.png';
function NavbarLogo() {
    return (
        <div className="header-components-container">
            {/*<div className="row">*/}
                <div className="brand-image">
                    <img src={logo} alt="logo"/>

                </div>
            <div className="brand-name">Baloot</div>


            {/*</div>*/}
        </div>
    );
}

export default NavbarLogo;