import React, { useEffect, useState } from 'react'
import { Card, Row, Col, Container } from "react-bootstrap";
import ReactPaginate from 'react-paginate';
import './pagination.css'

export default function PaginationDynamic() {
    const [offset, setOffset] = useState(0);
    const [perPage] = useState(10);
    const [pageCount, setPageCount] = useState(0)
    const [playerData, setPlayerData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch('https://nba-players.herokuapp.com/players-stats')
            const nbaData = await response.json()
            setPageCount(Math.ceil(nbaData.length/perPage))
            setPlayerData(nbaData.slice(offset, offset+perPage))
        }

        fetchData()
    }, [offset])

    const handlePageClick = (e) => {
        const selectedPage = e.selected;
        console.log(selectedPage*perPage)

        setOffset((selectedPage + 1)*perPage)

    }
    return (
        <Container>
            <Row>
                {playerData.map((playerData, k) => (
                    <Col key={k} xs={12} md={4} lg={3}>
                        <Card >
                            <Card.Img src="https://via.placeholder.com/150x75" />

                            <Card.Body>
                                <Card.Title>{playerData.name}</Card.Title>
                                <Card.Text>{playerData.team_name}</Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
            <ReactPaginate
                previousLabel={"prev"}
                nextLabel={"next"}
                breakLabel={"..."}
                breakClassName={"break-me"}
                pageCount={pageCount}
                marginPagesDisplayed={2}
                pageRangeDisplayed={5}
                onPageChange={handlePageClick}
                containerClassName={"pagination"}
                subContainerClassName={"pages pagination"}
                activeClassName={"active"}/>
        </Container>
    )
}