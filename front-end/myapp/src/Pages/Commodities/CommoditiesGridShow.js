import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import CommodityCard from "../Commodity/CommodityCard";
import IMAGE from '../../assets/images/item-in-cart.png'
import './Commodities.css'

import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Card from 'react-bootstrap/Card';



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

export default function CommoditiesGridShow(props) {
    const {items, searchType, searchValue} = props;
    const [commodities, setcommodities] = useState();
    const [fetchedCommodities, setFetchedCommodities] = useState();
    const [toggle, setToggle] = useState(true)

    const [filterBy, setFilterBy] = useState('all');
    // const [searchValue, setSearchValue] = useState("");

    const location = useLocation();


   // let filterName = (item) => {
   //      console.log('in name');
   //      return item.name.includes(searchValue);
   //  };

    const filterAvail = (item) => {
        if (item.inStock > 30) return true;
        return false;
    };
    const filterAll = (item) => {
       return true;

    };



    var searchTypesMap = {
        name: (item) => {
            return item.name.includes(searchValue);
        },
        provider: (item) => {
            return item.providerName.includes(searchValue);
        },
        category: (item) => {
            return item.categories.includes(searchValue);
        }
    };


    const getFilterFunc = (filterMode) => {
        if (filterMode === "available") return filterAvail;
        if (filterMode === "all") return filterAll;


    };


    useEffect(() => {
        if (!fetchedCommodities || !commodities) return;


        let newCommodity = fetchedCommodities.slice();
        console.log('after filter');

        if(searchValue!="") {
            searchValue.replace(/\+/g,' ')

            newCommodity = newCommodity.filter(searchTypesMap[searchType]);
        }
        newCommodity = newCommodity.filter(getFilterFunc(filterBy));
        // newCommodity = newCommodity.filter(getFilterFunc(filterBy));


        setcommodities(newCommodity);
    }, [filterBy,searchType, searchValue]);


    const handleSort = (basedOn) => {
        let compareFunction;
        if (basedOn === "price") {
            compareFunction = priceCompare;
        } else if (basedOn === "name") {
            compareFunction = nameCompare;
        }
        commodities.sort(compareFunction);
        setcommodities(commodities.slice());
    };

    const handleFilter = (toggle) => {
        setToggle(!toggle);
        console.log('in useeffect');
        console.log('toggle ');
        console.log(toggle);
        if(toggle){
            setFilterBy('available');
        }
        else{
            setFilterBy('all');

        }


    };

    useEffect(() => {
        async function fetchData() {
            try {
                const response = await axios.get("commodities");
                const commodititesList = response.data.content;

                setcommodities(commodititesList);

                setFetchedCommodities(commodititesList);

            } catch (e) {
                console.log(e);
            }
        }
        fetchData();
    }, []);
    return (
        <>

                <div className="filter-container">
                    <div className="col-1 mr-auto toggle">
                        Available Commodities:
                        <label>
                            <input type="checkbox" onClick={() => {
                                handleFilter(toggle);}} />
                            <span />
                        </label>

                    </div>

                    <div className="col-1 ml-auto">
                        <form>

                            <div className="form-row">

                                    Sort by:
                                <div className="col-1">
                                    <button type="button" className="btn button-type2" onClick={() => {
                                        handleSort("name");}}>name</button>
                                </div>
                                <div className="col-1">
                                    <button type="button" className="btn button-type2" onClick={() => {
                                        handleSort("price");}}>price</button>
                                </div>



                            </div>
                        </form>

                    </div>



            </div>


        <div className="commodities-list">

            {commodities &&
                commodities.map((item) => (
                  <div className="" key={item.id}>
                                                {/*<Link to={"/commoditites/" + item.id}>*/}
                      <CommodityCard
                           image={IMAGE}
                           name={item.name}
                           price={item.price}
                           count = {item.inStock}/>

                                                {/*</Link>*/}

                  </div>
            ))}
         </div>

        </>
    );
}
