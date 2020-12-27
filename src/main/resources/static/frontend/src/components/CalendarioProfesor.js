import React, { useState, useEffect } from 'react'
import { FullCalendar } from 'primereact/fullcalendar'
import { EventService } from '../service/EventService'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import '@fullcalendar/core/main.css'
import '@fullcalendar/daygrid/main.css'
import Auth from './Auth'

export const CalendarioProfesor = (props) => {
    const [events, setEvents] = useState([])
    const [auth, setAuth] = useState(true)
    const options = {
        plugins: [dayGridPlugin, interactionPlugin],
        defaultView: 'dayGridMonth',
        defaultDate: '2020-12-01',
        header: {
            left: 'prev,next',
            center: 'title',
            right: 'dayGridMonth'
        },
        editable: true,
        height: 800,
        eventDrop: function(info){
            eventService.updateEvent(props.urlBase, info.event.id, info.event.start,
                info.event.end)
        },
        eventClick: function(info){
            console.log(info.event)
        }
    }

    const eventService = new EventService()

    useEffect(() => {
        eventService.getEvents(props.urlBase).then(data => setEvents(data.data)).catch(error => setAuth(false))
    }, []) // eslint-disable-line react-hooks/exhaustive-deps

    if(!auth){
        return <Auth authority="profesor"></Auth>
    }else{
        return (
            <div>
                <div className="card">
                    <FullCalendar events={events} options={options}/>
                </div>
            </div>
        )
    }
}