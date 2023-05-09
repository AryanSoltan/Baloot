
import React, { useState, useEffect } from 'react';


import Header from "../../components/Header/Header";
import CommoditiesGridShow from "../Commodities/CommoditiesGridShow";
import CommodityInfo from "./CommodityInfo";
import Comments from "./Comments";
import './Commodity.css'
import CommentsPart from "./CommentsPart";
import SuggestionsPart from "../SuggestionsPart";

import {Context} from "../../App";
import Footer from "../Footer";


function Commodity() {

    const [items, setItems] = useState([]);
    const [value, setValue] = useState(0);

    return (
        <body className="page-container container">
        <Context.Provider value={{value, setValue}}>
        <header>
            <Header/>
        </header>
        <main>
            {/*<CommoditiesFilterBox />*/}

            <CommodityInfo />
        <CommentsPart />
                <SuggestionsPart/>


            {/*<Comments />*/}


        </main>
        </Context.Provider>
        <Footer/>

        </body>







    );
}

export default Commodity;