
import React, { useState, useEffect } from 'react';


import Header from "../../components/Header/Header";
import './Commodities.css'

import CommoditiesGridShow from "./CommoditiesGridShow";

import { Link, useLocation } from "react-router-dom";

import {Context} from "../../App";


function Commodities() {

    const [items, setItems] = useState([]);
    const [filterBy, setFilterBy] = useState();
    const [searchValue, setSearchValue] = useState("");

    const [value, setValue] = useState(0);


    const location = useLocation();

    useEffect(() => {
        if (location.search) {
            const searchText = location.search;
            const params = new URLSearchParams(searchText);

            const filter = params.get("filterBy");
            const searchvalue = params.get("searchValue");
            console.log('filteer name is ');
            console.log(filter);

            setFilterBy(filter);
            setSearchValue(searchvalue);
        }
    }, [location.search]);



    return (

        <body className="page-container container">
        <Context.Provider value={{value, setValue}}>


            <header>
            <Header  hasSearch = "1" />
            </header>
            <main>
            {/*<CommoditiesFilterBox />*/}

<CommoditiesGridShow items={items} searchType={filterBy} searchValue={searchValue}/>

            </main>
        </Context.Provider>


        </body>







    );
}



export default Commodities;