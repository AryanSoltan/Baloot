
import React, { useState, useEffect } from 'react';


import Header from "../../components/Header/Header";
import CommoditiesGridShow from "../Commodities/CommoditiesGridShow";
import CommodityInfo from "./CommodityInfo";
import Comments from "./Comments";
import './Commodity.css'
import CommentsPart from "./CommentsPart";
import SuggestionsPart from "../SuggestionsPart";


function Commodity() {

    const [items, setItems] = useState([]);

    return (
        <body className="page-container container">
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

        </body>






    );
}

export default Commodity;