import {Button, DatePicker, Form, Input, InputNumber, Modal, notification, Row, Space} from "antd";
import {useDispatch, useSelector} from "react-redux";
import {setIsTicketModalOpen, setMessage} from "../../store/action/pageAction";
import dayjs from "dayjs";
import React from "react";
import {useState} from "react";
import api from "../../service/tripInstance";

const FormTicket = () => {
    const [form] = Form.useForm();
    const [notificationApi, contextHolder] = notification.useNotification();
    const page = useSelector(store => store.page);
    const dispatch = useDispatch();

    const [ticketId, setTicketId] = useState("");
    const [passengerId, setPassengerId] = useState("");
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [birthDate, setBirthDate] = useState("2001-01-01");
    const [routeId, setRouteId] = useState("");
    const [departureDate, setDepartureDate] = useState("2001-01-01");
    const [place, setPlace] = useState("");
    const [price, setPrice] = useState("");

    const dateFormat = 'YYYY-MM-DD';
    const fullDateFormat = 'YYYY-MM-DDT00:00:00.0000h00';

    const openNotification = (type, message) => {
        notificationApi[type]({
            message: 'Notification',
            description: message,
        });
    };

    const closeModal = () => {
        dispatch(setIsTicketModalOpen(false));
    }

    const handleOk = () => {
        form.submit();
    }

    const handleCancel = () => {
        closeModal();
    }

    const handleReset = () => {
        form.resetFields();
    }

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    const onFinish = (values) => {
        closeModal();
        let data = {
            id: ticketId,
            routeId: routeId,
            passengerId: passengerId,
            name: name,
            surname: surname,
            birthDate: birthDate,
            departureDate: departureDate,
            place: place,
            price: price,
        };
        api.post('/tickets', data)
            .then(res => {
                console.log(res);
                if (res.status === 200 && res.data.code === undefined) {
                    dispatch(setMessage("Ticket successfully added!"));
                    openNotification('success', "Ticket successfully added!");
                    //todo: refresh if passengerId matches
                } else {
                    console.log(res);
                    dispatch(setMessage(res.data.message));
                    openNotification('error', res.data.message);
                }
                closeModal();
            })
            .catch(err => {
                console.log(err);
                dispatch(setMessage(err.response.data.error.message));
                openNotification('error', err.response.data.error.message);
            })
    }

    return (
        <>
            {contextHolder}
            <Modal title="Ticket"
                   width={'50%'}
                   open={page.is_ticket_modal_open}
                   onOk={handleOk}
                   onCancel={handleCancel}
                   footer={[
                       <Button key='reset' htmlType='button' onClick={handleReset}>Reset</Button>,
                       <Button key='back' htmlType='button' type='primary' onClick={handleCancel}>Cancel</Button>,
                       <Button key='submit' htmlType='submit' type='primary' onClick={handleOk}>OK</Button>
                   ]}
            >
                <Form form={form} labelCol={{span: 4}} wrapperCol={{span: 14}} layout="horizontal"
                      onFinish={onFinish}
                      onFinishFailed={onFinishFailed}
                      autoComplete="off">
                    <Form.Item label='ID:' name='ticketId' hidden shouldUpdate={true}>
                        <InputNumber min={1}
                                     value={ticketId}
                                     onValueChange={e => setTicketId(e)}
                                     disabled/>
                    </Form.Item>
                    <Form.Item label="Passenger">
                        <Row>
                            <Form.Item name='passengerId' rules={[{
                                required: true,
                                message: 'Please input passenger ID!',
                            }]}>
                                <Input placeholder='Passenger ID' value={passengerId}
                                       onChange={e => setPassengerId(e.target.value)}/>
                            </Form.Item>
                        </Row>
                        <Row>
                            <Form.Item name='name' rules={[{
                                required: true,
                                message: 'Please input name!',
                            }]}>
                                <Input placeholder='Name' value={name} onChange={e => setName(e.target.value)}/>
                            </Form.Item>
                        </Row>
                        <Row>
                            <Form.Item name='surname' rules={[{
                                required: true,
                                message: 'Please input surname!',
                            }]}>
                                <Input placeholder='Surname' value={surname}
                                       onChange={e => setSurname(e.target.value)}/>
                            </Form.Item>
                        </Row>
                        <Row>
                            <Form.Item label="Birth Date" name='birthDate' required>
                                <Row>
                                    <DatePicker
                                        format={dateFormat}
                                        value={dayjs(birthDate, dateFormat)}
                                        onChange={e => setBirthDate(dayjs(e).format(fullDateFormat))}/>
                                </Row>
                            </Form.Item>
                        </Row>
                    </Form.Item>
                    <Form.Item label="Route ID:" name='routeId' rules={[{
                        required: true,
                        message: 'Please input route ID!',
                    }]}>
                        <InputNumber
                            min={1}
                            value={routeId}
                            onChange={e => setRouteId(e)}
                            disabled={false}/>
                    </Form.Item>
                    <Form.Item label="Departure Date" name='departureDate' required>
                        <Row>
                            <DatePicker
                                format={dateFormat}
                                value={dayjs(departureDate, dateFormat)}
                                onChange={e => setDepartureDate(dayjs(e).format(fullDateFormat))}
                                disabled={false} />
                        </Row>
                    </Form.Item>
                    <Form.Item label="Place" name='place' rules={[{
                        required: true,
                        message: 'Please input place!',
                    }]}>
                        <Input
                            placeHolder="Place"
                            value={place}
                            onChange={e => setPlace(e.target.value)}/>
                    </Form.Item>
                    <Form.Item label="Price" name='price' hidden>
                        <InputNumber
                            min={1}
                            value={price}
                            onChange={e => setPrice(e)}
                            disabled={true}/>
                    </Form.Item>
                </Form>
            </Modal>
        </>
    );
}
export default FormTicket;