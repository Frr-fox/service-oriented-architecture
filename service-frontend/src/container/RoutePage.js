import {Button, Col, Divider, InputNumber, Layout, notification, Row, Space} from "antd";
import React, {useEffect, useState} from 'react';
import TableRoute from "../component/tables/TableRoute";
import {useDispatch, useSelector} from "react-redux";
import {
    needRefresh,
    setData,
    setInstance,
    setIsBetweenModal,
    setIsDateHidden,
    setIsIdHidden,
    setIsModalOpen, setMessage,
    setModalType
} from "../store/action/pageAction";
import {addQueryParams, ModalType} from "../utils/ModalType";
import {store} from "../store/store";
import api from "../service/axiosInstance";
import {FormRoute} from "../component/forms/FormRoute";

export const RoutePage = () => {
    const page = useSelector(store => store.page);
    const dispatch = useDispatch();

    const [distance, setDistance] = useState(1);
    const [showId, setShowId] = useState(1);
    const [updateId, setUpdateId] = useState(1);
    const [deleteId, setDeleteId] = useState(1);

    const [notificationApi, contextHolder] = notification.useNotification();

    const handleModal = (modalType, requiredId) => {
        if (modalType === ModalType.ADD) {
            dispatch(setModalType(modalType));
            syncModalType();
        } else {
            api.get('/routes/' + requiredId)
                .then(res => {
                    if (res.data.code !== undefined) {
                        dispatch(setMessage("Something goes wrong"));
                        openNotification('error');
                    } else {
                        dispatch(setInstance(res.data));
                        dispatch(setModalType(modalType));
                        syncModalType();
                    }
                })
                .catch(err => {
                    dispatch(setMessage(err.response.data.message));
                    openNotification('error');
                })
        }
    }

    const handleShow = () => {
        handleModal(ModalType.SHOW, showId);
    }

    const handleAdd = () => {
        handleModal(ModalType.ADD, "")
    }

    const handleUpdate = (route) => {
        handleModal(ModalType.UPDATE, updateId)
    }

    const handleDelete = () => {
        api.delete('/routes/' + deleteId)
            .then(res => {
                if (res.status === 200 && res.data.code === undefined) {
                    dispatch(setMessage("Route with ID = " + deleteId + " was successfully deleted!"));
                    openNotification('success');
                    dispatch(needRefresh(true));
                } else {
                    dispatch(setMessage("Something goes wrong"));
                    openNotification('error');
                }
            })
            .catch(err => {
                dispatch(setMessage(err.response.data.message));
                openNotification('error');
            })
    }

    const handleMinTo = () => {
        api.get('/routes/to/min')
            .then(
                res => {
                    if (res.status === 200 && res.data.code === undefined) {
                        let instance = res.data;
                        instance.creationDate = instance.creationDate.substring(0, 10);
                        dispatch(setInstance(instance));
                        dispatch(setModalType(ModalType.SHOW));
                        syncModalType();
                    } else {
                        dispatch(setMessage("Something goes wrong"));
                        openNotification('error');
                    }
                }
            )
            .catch(err => {
                dispatch(setMessage(err.response.data.message));
                openNotification('error');
        })
    }

    const handleDistanceGreaterThan = () => {
        api.get('/routes/distance/greater-than/' + distance + addQueryParams({paramName: "page", paramValue: 1}, {paramName: "limit", paramValue: 100})) //todo: добавить параметры в запрос
            .then(
                res => {
                    if (res.status === 200 && res.data.code === undefined) {
                        dispatch(setData(res.data));
                        dispatch(needRefresh('partly'));
                        dispatch(setMessage("Routes with distance greater than " + distance + " are in the table. To show all routes refresh data"))
                        openNotification('info');
                    } else {
                        dispatch(setMessage("Something goes wrong"));
                        openNotification('error');
                    }
                }
            )
            .catch(err => {
                dispatch(setMessage(err.response.data.message));
                openNotification('error');
            })
    }

    const handleCount = () => {
        api.get('/routes/distance/greater-than/' + distance + '/count')
            .then(
                res => {
                    if (res.status === 200) {
                        dispatch(setMessage("Routes with distance greater than " + distance + " is " + res.data + "!"));
                        openNotification('success');
                    } else {
                        dispatch(setMessage("Something goes wrong"));
                        openNotification('error');
                    }
                }
            )
            .catch(err => {
                dispatch(setMessage(err.response.data.message));
                openNotification('error');
            })
    }

    const openNotification = (type) => {
        notificationApi[type]({
            message: 'Notification',
            description: store.getState().page.msg,
        });
    };

    const syncModalType = () => {
        switch (store.getState().page.modal_type) {
            case ModalType.ADD:
                dispatch(setIsModalOpen(true))
                dispatch(setIsIdHidden(true))
                dispatch(setIsDateHidden(true))
                dispatch(setIsBetweenModal(false))
                break;
            case ModalType.SHOW:
                dispatch(setIsModalOpen(true))
                dispatch(setIsIdHidden(false))
                dispatch(setIsDateHidden(false))
                dispatch(setIsBetweenModal(false))
                break;
            case ModalType.UPDATE:
                dispatch(setIsModalOpen(true))
                dispatch(setIsIdHidden(true))
                dispatch(setIsDateHidden(false))
                dispatch(setIsBetweenModal(false))
                break;
            case ModalType.BETWEEN:
                dispatch(setIsModalOpen(true))
                dispatch(setIsIdHidden(true))
                dispatch(setIsDateHidden(true))
                dispatch(setIsBetweenModal(true))
                break;
            default:
                dispatch(setIsModalOpen(false))
                break;
        }
    }

        return (
            <Layout className='page-content'>
                {contextHolder}
                <TableRoute/>
                <Row>
                    <Col span={12}>
                        <Row align='center' style={{padding: '5% 0'}}>
                            <Row align='center'>
                                <Space direction='vertical'>
                                    <Row>
                                        <Space direction='horizontal'>
                                            <p>ID:</p>
                                            <InputNumber min={1} defaultValue={1} value={showId} onChange={e => setShowId(e)}/>
                                            <Button type='primary' onClick={handleShow}>Show route</Button>
                                        </Space>
                                    </Row>
                                    <Row>
                                        <Space direction='horizontal'>
                                            <p>ID:</p>
                                            <InputNumber min={1} defaultValue={1} value={updateId} onChange={e => setUpdateId(e)}/>
                                            <Button type='primary' onClick={handleUpdate}>Update route</Button>
                                        </Space>
                                    </Row>
                                    <Row>
                                        <Space direction='horizontal'>
                                            <p>ID:</p>
                                            <InputNumber min={1} defaultValue={1} value={deleteId} onChange={e => setDeleteId(e)}/>
                                            <Button type='primary' onClick={handleDelete}>Delete route</Button>
                                        </Space>
                                    </Row>
                                    <Row align='center'>
                                        <Button type='primary' onClick={handleAdd}>Add Route</Button>
                                    </Row>
                                </Space>
                            </Row>
                        </Row>
                    </Col>
                    <Col span={1}>
                        <Divider type='vertical'/>
                    </Col>
                    <Col span={10} >
                        <Space direction='vertical' style={{padding: '5% 0'}}>
                            <Row>
                                <Space direction='horizontal'>
                                    <p>Route with minimal To field</p>
                                    <Button type='primary' onClick={handleMinTo}>Show</Button>
                                </Space>
                            </Row>
                            <Row>
                                <Space direction='horizontal'>
                                    <p>Routes with distance greater than</p>
                                    <InputNumber min={1} defaultValue={1} value={distance} onChange={e => setDistance(e)}/>
                                    <Space/>
                                    <Button type='primary' onClick={handleDistanceGreaterThan}>Show</Button>
                                    <Button type='primary' onClick={handleCount}>Count</Button>
                                </Space>
                            </Row>
                        </Space>
                    </Col>
                </Row>
                <FormRoute/>
            </Layout>
        )
}