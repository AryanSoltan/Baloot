
import React, { useState, useEffect } from 'react';


import Header from "../../components/Header/Header";
import './Commodities.css'

import CommoditiesGridShow from "./CommoditiesGridShow";

import { Link, useLocation } from "react-router-dom";

function Commodities() {
    const [items, setItems] = useState([]);
    const [filterBy, setFilterBy] = useState();
    const [searchValue, setSearchValue] = useState("");

    const location = useLocation();

    useEffect(() => {
        if (location.search) {
            const searchText = location.search;
            const params = new URLSearchParams(searchText);

            const filter = params.get("filterBy");
            const value = params.get("searchValue");
            console.log('filteer name is ');
            console.log(filter);

            setFilterBy(filter);
            setSearchValue(value);
        }
    }, [location.search]);



    return (

        <body className="page-container container">

            <header>
            <Header  hasSearch = "1" />
            </header>
            <main>
            {/*<CommoditiesFilterBox />*/}

<CommoditiesGridShow items={items} searchType={filterBy} searchValue={searchValue}/>

            </main>

        </body>






    );
}

export default Commodities;