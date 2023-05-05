
import React, { useState, useEffect } from 'react';


import Header from "../components/Header/Header";
// import './Commodities/Commodities.css'

import Provider from "./Provider";
import './Provider.css'

import { Link, useLocation } from "react-router-dom";

function ProviderPage() {




    return (

        <body className="page-container container">

        <header>
            <Header  hasSearch = "1" />
        </header>
        <main>
            <Provider />

        </main>

        </body>






    );
}

export default ProviderPage;