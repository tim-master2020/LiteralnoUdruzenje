import { Card } from '@material-ui/core';
import React from 'react';
import { Carousel } from 'react-bootstrap';
import Appbar from '../../components/appbar/Appbar';
import photo1 from '../../icons/photo1.svg';
import photo2 from '../../icons/photo2.svg';
import photo3 from '../../icons/photo3.svg';

const HomePage = () => {
    return (
        <div>
            <Appbar />
            <Carousel style={{heigth:'30%',width:'40%',marginLeft:'30%',marginTop:'2%'}}>
                <Carousel.Item>
                    <img className="d-block w-100"src={photo1} style={{height:'inherit'}}/>
                </Carousel.Item>
                <Carousel.Item>
                    <img className="d-block w-100"src={photo2} style={{height:'inherit'}}/>
                </Carousel.Item>
                <Carousel.Item>
                    <img className="d-block w-100"src={photo3} style={{height:'inherit'}}/>
                </Carousel.Item>
            </Carousel>
        </div>
    );
}
export default HomePage;