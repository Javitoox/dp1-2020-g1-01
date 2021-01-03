import React, { useState, useEffect } from 'react'
import { FullCalendar } from 'primereact/fullcalendar'
import { EventService } from '../service/EventService'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import '@fullcalendar/core/main.css'
import '@fullcalendar/daygrid/main.css'
import Auth from './Auth'
import { Dialog } from 'primereact/dialog'
import moment from 'moment'

export const CalendarioAlumno = (props) => {
    const [events, setEvents] = useState(null)
    const [auth, setAuth] = useState(true)
    const [info, setInfo] = useState(null)


    function selectInfo(info, id) {
        var parts = info.split("/")
        return (
            <Dialog header="Information" visible={true} style={{ width: '25vw' }} onHide={() => setInfo(null)}>
                <p><b>Description:</b> {parts[0]}</p>
                <p><b>Type:</b> {parts[1]}</p>
            </Dialog>
        )
    }
    function actualState() {
        return <div>
            {events}
            {info}
        </div>
    }

    const eventService = new EventService()

    const options = {
        plugins: [dayGridPlugin, interactionPlugin],
        defaultView: 'dayGridMonth',
        defaultDate: moment().format('YYYY-MM-DD'),
        header: {
            left: 'prev,next',
            center: 'title',
            right: 'dayGridMonth'
        },
        editable: false,
        height: 800,
        eventClick: function (info) {
            eventService.getDescription(props.urlBase, info.event.id).then(data =>
                setInfo(selectInfo(data.data, info.event.id))
            )
        }
    }

    useEffect(() => {
        console.log({auth})
        eventService.getEvents(props.urlBase).then(data => setEvents(
            <div className="card">
                <FullCalendar events={data.data} options={options} />
            </div>
        )).catch(error => setAuth(false))
    }, []) // eslint-disable-line react-hooks/exhaustive-deps

     if (!auth) {
         return <Auth authority="alumno"></Auth>
     } else {
        return actualState()
    }
}