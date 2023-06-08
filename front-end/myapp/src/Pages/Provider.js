import axios from "axios";
import React, {useEffect, useMemo, useState} from "react";
import {Link, useLocation, useParams} from "react-router-dom";
import CommodityCard from "./Commodity/CommodityCard";
import IMAGE from '../assets/images/item-in-cart.png'
// import './Commodities/Commodities.css'


import './Provider.css'

import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import Container from 'react-bootstrap/Container';
import Card from 'react-bootstrap/Card';
import ReactPaginate from "react-paginate";



const nameCompare = (a, b) => {
    if (a.name > b.name) return 1;
    else if (a.name === b.name) return 0;
    else return -1;
};


const priceCompare = (a, b) => {
    if (a.price > b.price) return 1;
    else if (a.price === b.price) return 0;
    else return -1;
};

export default function Provider(props) {

    const [provider, setProvider] = useState([]);

    const { id } = useParams();


    useEffect(() => {
        async function fetchData() {
            try {

                const response = await axios.get('providers/' + id,  {headers: {Authorization: localStorage.getItem('token')}});
                const providerR= response.data.content;
                console.log('pro');
                console.log(providerR);

                setProvider(providerR);
            } catch (e) {
                if(e.response.status === 404) {

                }
            }
        }

        fetchData();

    }, []);

    return (
        <>

            <div className="info-container">
                <img className="provider-image" src={provider.image} />
                <div className="date-info">since {provider.registryDate && provider.registryDate.split('-')[0]}</div>
                <div className="providerName">{provider.name}</div>

            </div>

            <div className="suggestions-section">
                <div className="suggestionTitle">All provided commodities</div>
            <div className="commodities-list">

                {provider.commodities &&
                    provider.commodities.map((item,k) => (
                        <div className="" key={item.id}>

                                <CommodityCard
                                    commodity={item}
                                />

                        </div>
                    ))}

            </div>
            </div>




        </>
    );
}
