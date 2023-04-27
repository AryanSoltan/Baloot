import React from "react";
import './Navbar.css'
import logo from '/home/saba/Saba/UT/Term8/IE/ca5-git/Baloot/front-end/myapp/src/assets/images/ballot.png';
function NavbarLogo() {
    return (

        <a href="/" className="home-link">
            <div className="brand-image">
                <img
                    src={logo}
                    className="d-inline-block align-top icon img-responsive "
                    alt="logo"
                />
            </div>
            <span className="brand-name">Baloot</span>
        </a>

    );
}

export default NavbarLogo;