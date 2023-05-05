import axios from "axios";
import React, {useEffect, useMemo, useState} from "react";
import { Link, useLocation } from "react-router-dom";
import CommodityCard from "../Commodity/CommodityCard";
import IMAGE from '../../assets/images/item-in-cart.png'
import './Commodities.css'
import './pagination.css'

import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Card from 'react-bootstrap/Card';
import ReactPaginate from "react-paginate";
import commodity from "../Commodity/Commodity";



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
    const [commodities, setcommodities] = useState([]);
    const [fetchedCommodities, setFetchedCommodities] = useState();
    const [toggle, setToggle] = useState(true)
    const [sortType, setSortType] = useState();

    const [filterBy, setFilterBy] = useState('all');
    const [offset, setOffset] = useState(0);
    const [perPage] = useState(12);
    const [pageCount, setPageCount] = useState(0)



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

    var sortTypesMap = {
        'name': nameCompare,
        'price': priceCompare
    };


    const getFilterFunc = (filterMode) => {
        console.log('filter mod ei s');
        console.log(filterMode);
        if (filterMode === "available") return filterAvail;
        if (filterMode === "all") return filterAll;


    };


    useMemo(() => {
        if (!fetchedCommodities) return;
        //
        //
        let newCommodity = fetchedCommodities.slice();
        //let newCommodity = commodities.slice();
        console.log('after filter');

        if(searchValue!="") {
            searchValue.replace(/\+/g,' ')

            newCommodity = newCommodity.filter(searchTypesMap[searchType]);
        }
        newCommodity = newCommodity.filter(getFilterFunc(filterBy));
        newCommodity.sort(sortTypesMap[sortType]);

        console.log('new coms are');
        console.log('sort type');
        console.log(sortType);
        console.log(newCommodity);
        // newCommodity = newCommodity.filter(getFilterFunc(filterBy));




        setPageCount(Math.ceil(newCommodity.length/perPage))

        setcommodities(newCommodity.slice(offset, offset+perPage))
        // handleSort()

    }, [sortType,filterBy,searchType, searchValue,offset]);


    const handleSort = (basedOn) => {
        let compareFunction;
        if (basedOn === "price") {
            compareFunction = priceCompare;
            setSortType('price');
        } else if (basedOn === "name") {
            compareFunction = nameCompare;
            setSortType('name');
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
                console.log('in fetch data');
                const response = await axios.get("commodities");
                const commodititesList = response.data.content;

                setcommodities(commodititesList);
                setFetchedCommodities(commodititesList);

                setPageCount(Math.ceil(commodititesList.length/perPage))

                setcommodities(commodititesList.slice(offset, offset+perPage))

            } catch (e) {
                console.log(e);
            }
        }
        fetchData();
    }, []);

    const handlePageClick = (e) => {
        const selectedPage = e.selected;
        setOffset((selectedPage )*perPage)

    };
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

                            <div className="form-row ">
                                <span className="sortBy">Sort by:</span>
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
                commodities.map((item,k) => (
                  <div className="" key={item.id}>
                      <Col key={k} xs={12} md={4} lg={3}>
                                                {/*<Link to={"/commoditites/" + item.id}>*/}
                      <CommodityCard
                           commodity={item}

                      />

                                                {/*</Link>*/}
                      </Col>
                  </div>
            ))}

         </div>
            {pageCount>1 && <ReactPaginate
                activeClassName={'item active '}
                breakClassName={'item break-me '}
                breakLabel={'...'}
                containerClassName={'pagination'}
                disabledClassName={'disabled-page'}
                marginPagesDisplayed={2}
                nextClassName={"item next"}
                nextLabel={">"}
                onPageChange={handlePageClick}
                pageCount={pageCount}
                pageClassName={'item pagination-page '}
                pageRangeDisplayed={1}
                previousClassName={"item previous"}
                previousLabel={"<"}
            />}

        </>
    );
}
