import React, { useState, useEffect } from 'react'
import { FullCalendar } from 'primereact/fullcalendar'
import { EventService } from '../service/EventService'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import '@fullcalendar/core/main.css'
import '@fullcalendar/daygrid/main.css'
import Auth from './Auth'
import { Dialog } from 'primereact/dialog'
import { Button } from 'primereact/button'
import { FormEvent } from './FormEvent'
import moment from 'moment'

export const CalendarioProfesor = (props) => {
    const [events, setEvents] = useState(null)
    const [auth, setAuth] = useState(true)
    const [info, setInfo] = useState(null)

    function remove(id) {
        eventService.deleteEvent(props.urlBase, id).then(data => setEvents(
            <div className="card">
                <FullCalendar events={data.data} options={options} />
                <Button label="Create" className="p-button-success" onClick={() => setInfo(formCreate())}></Button>
            </div>
        ))
        setInfo(null)
    }

    function calculateCourse(course){
        if(!course){
            return "'Event assigned to a course without students'";
        }else{
            return course;
        }
    }

    function inscriptions(parts){
        var result = []
        var index = 0
        for(var i=3;i<parts.length;i++){
            result[index] = <p><center>{parts[i]}</center></p>
            index++
        }
        if(result.length === 0)
            return <p><center>'There are not inscriptions'</center></p>
        else
            return result
    }

    function selectInfo(info, id) {
        var parts = info.split("/")
        return (
            <Dialog header="Information" visible={true} style={{ width: '25vw' }} onHide={() => setInfo(null)}>
                <p><b>Description:</b> {parts[0]}</p>
                <p><b>Type:</b> {parts[1]}</p>
                <p><b>Course:</b> {calculateCourse(parts[2])}</p>
                <p className="lead"><center><u>Inscriptions</u></center></p>
                {inscriptions(parts)}
                <Button label="Delete" className="p-button-danger" onClick={() => remove(id)}></Button>
            </Dialog>
        )
    }

    function act(data){
        eventService.getEvents(props.urlBase).then(data => setEvents(
            <div className="card">
                <FullCalendar events={data.data} options={options} />
                <Button label="Create" className="p-button-success" onClick={() => setInfo(formCreate())}></Button>
            </div>
        ))
    }

    function formCreate(){
        return (
            <Dialog header="Create event" visible={true} style={{ width: '25vw' }} onHide={() => setInfo(null)}>
                <FormEvent urlBase={props.urlBase} act={act}></FormEvent>
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
        editable: true,
        height: 800,
        eventDrop: function (info) {
            eventService.updateEvent(props.urlBase, info.event.id, info.event.start,
                info.event.end)
        },
        eventResize: function (info) {
            eventService.updateEvent(props.urlBase, info.event.id, info.event.start,
                info.event.end)
        },
        eventClick: function (info) {
            eventService.getDescription(props.urlBase, info.event.id).then(data =>
                setInfo(selectInfo(data.data, info.event.id))
            )
        }
    }

    useEffect(() => {
        eventService.getEvents(props.urlBase).then(data => setEvents(
            <div className="card">
                <FullCalendar events={data.data} options={options} />
                <Button label="Create" className="p-button-success" onClick={() => setInfo(formCreate())}></Button>
            </div>
        )).catch(error => setAuth(false))
    }, []) // eslint-disable-line react-hooks/exhaustive-deps

    if (!auth) {
        return <Auth authority="profesor"></Auth>
    } else {
        return actualState()
    }
}