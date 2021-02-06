import React, { Component } from 'react';
import "../css/aboutus.css";
import { Card } from 'primereact/card';

export class AboutUs extends Component {
    render(){
        return(
            <React.Fragment>
            <div className="imagenMap">
                <center><img src="/mapAcademy.png" alt="Map"></img></center>
            </div>
            <div className="padre">
                <Card className="hijo mr-3 mt-4 ml-2" title="Questions">
                    <p>Visitors are always welcome to our school. If there’s anything you’d like to know about our methods, please get in touch.
​                    <br></br>Todo el mundo siempre es bienvenido a nuestra escuela. Si hay algo que te gustaría saber sobre nuestros métodos, ponte en contacto.</p>
                    <center><p>
                        <b>Contact us</b><br></br>
                        Calle San Francisco, nº 15<br></br>
                        <a href="mailto:maribelrb.5@gmail.com">teainalcala@gmail.com</a><br></br>
                        +34 690 350 543<br></br>
                        Isabel
                    </p></center>
                </Card> 
                <Card className="hijo ml-3 mt-4 mr-2" title="About Our Language School">
                    <p><b>TEA THE ENGLISH ACADEMY</b> opened its doors to the population, both  young and old and all in-between,  of Alcala de Guadaira, with the aim of bringing a diverse teaching strategy to our beautiful town. We are a small family-run English language centre who take great pride in our abilities and great pleasure in teaching our students.</p>
                    <p><b>TEA THE ENGLISH ACADEMY</b> abrió sus puertas a la población de Alcalá de Guadaira, tanto a niños como a adultos, con el objetivo de llevar una estrategia diversa de enseñanza  a nuestra hermosa ciudad. Somos un pequeño centro de idioma de inglés dirigido por una familia que se enorgullece de sus habilidades y de su gran placer de enseñar.</p>
                </Card>
            </div>
            </React.Fragment>
        );
    }
}