
import React, { useState, useEffect } from 'react';


import Header from "../components/Header/Header";
import './Commodities.css'
import CommoditiesFilterBox from "./CommoditiesFilterBox";


function Commodities() {


    return (
        <body className="page-container container">
            <header>
            <Header />
            </header>
            <main>
            <CommoditiesFilterBox />
{/*<CommoditiesGridShow/>*/}

            </main>
        </body>






    );
}

export default Commodities;