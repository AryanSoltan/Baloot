
import React, { useState, useEffect } from 'react';


import Header from "../components/Header/Header";
import './Commodities.css'
import CommoditiesFilterBox from "./CommoditiesFilterBox";
import CommoditiesGridShow from "./CommoditiesGridShow";


function Commodities() {
    const [items, setItems] = useState([]);

    return (
        <body className="page-container container">
            <header>
            <Header />
            </header>
            <main>
            {/*<CommoditiesFilterBox />*/}

<CommoditiesGridShow items={items} />

            </main>

        </body>






    );
}

export default Commodities;