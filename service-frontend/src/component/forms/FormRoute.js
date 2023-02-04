import React, {useEffect} from 'react';
import {
    Button, DatePicker,
    Form,
    Input,
    InputNumber, Modal,
    Row,
    Space,
    notification
} from 'antd';
import dayjs from 'dayjs';
import {
    needRefresh,
    setInstance,
    setIsModalOpen, setMessage,
    setModalType
} from "../../store/action/pageAction";
import {useDispatch, useSelector} from "react-redux";
import {store} from "../../store/store";
import {initialInstance} from "../../store/reducer/pageReducer";
import {ModalType} from "../../utils/ModalType";
import api from "../../service/axiosInstance";
import apiRemote from "../../service/axiosRemoteInstance";

export const FormRoute = () => {
    const [form] = Form.useForm();
    const [notificationApi, contextHolder] = notification.useNotification();
    const page = useSelector(store => store.page);
    const dispatch = useDispatch();

    const dateFormat = 'YYYY-MM-DD';
    const fullDateFormat = 'YYYY-MM-DDT00:00:00.0000h00';

    useEffect(() => {
        form.setFieldsValue({
            id: page.instance.id,
            name: page.instance.name,
            x: page.instance.x,
            y: page.instance.y,
            creationDate: page.instance.creationDate,
            fromX: page.instance.fromX,
            fromY: page.instance.fromY,
            fromZ: page.instance.fromZ,
            toName: page.instance.toName,
            toX: page.instance.toX,
            toY: page.instance.toY,
            toZ: page.instance.toZ,
            distance: page.instance.distance,
        });
    }, [page.instance])

    useEffect(() => {
        if (page.modal_type === ModalType.ADD || page.modal_type === ModalType.BETWEEN) {
            handleReset();
        }
    }, [page.modal_type])

    const closeModal = () => {
        dispatch(setModalType(false));
        dispatch(setIsModalOpen(false));
    }

    const handleOk = () => {
        form.submit();
        // switch (page.modal_type) {
        //     case ModalType.UPDATE:
        //         form.submit();
        //         if (form.isFieldsValidating("name", "x", "y", "fromX", "fromY", "fromZ", "distance")) {
        //             submitUpdate();
        //         }
        //         break;
        //     case ModalType.ADD:
        //         form.submit();
        //         if (form.isFieldsValidating("name", "x", "y", "fromX", "fromY", "fromZ", "distance")) {
        //             submitAdd();
        //         }
        //         break;
        //     case ModalType.BETWEEN:
        //         form.submit();
        //         if (form.isFieldsValidating("name", "x", "y", "fromId", "toId", "distance")) {
        //             submitAddBetween();
        //         }
        //         break;
        //     default:
        //         break;
        }
        // closeModal();

    const submitUpdate = () => {
        api.put('/routes/' + page.instance.id, page.instance)
            .then(res => {
                if (res.status === 200 && res.data.code === undefined) {
                    let message ="Route with ID = " + page.instance.id + " was successfully updated!";
                    dispatch(setMessage(message));
                    dispatch(needRefresh(true));
                    openNotification('success', message);
                } else {
                    dispatch(setMessage(res.data.message));
                    openNotification('error', res.data.message);
                }
                closeModal();
            })
            .catch(err => {
                dispatch(setMessage(err.response.data.message));
                openNotification('error', err.response.data.message);
            })
    }

    const submitAdd = () => {
        let data = {
            name: page.instance.name,
            x: page.instance.x,
            y: page.instance.y,
            fromX: page.instance.fromX,
            fromY: page.instance.fromY,
            fromZ: page.instance.fromZ,
            toName: page.instance.toName,
            toX: page.instance.toX,
            toY: page.instance.toY,
            toZ: page.instance.toZ,
            distance: page.instance.distance,
        };
        api.post('/routes', data)
            .then(res => {
                console.log(res);
                if (res.status === 200 && res.data.code === undefined) {
                    let message = "Route successfully added!";
                    dispatch(setMessage(message));
                    openNotification('success', message);
                    dispatch(needRefresh(true));
                } else {
                    dispatch(setMessage(res.data.message));
                    openNotification('error', res.data.message);
                }
                closeModal();
            })
            .catch(err => {
                dispatch(setMessage(err.response.data.message));
                openNotification('error', err.response.data.message);
            })
    }

    const submitAddBetween = () => {
        let data = {name: page.instance.name, x: page.instance.x, y: page.instance.y};
        apiRemote.post('/navigator/route/add/' + page.instance.fromId + "/" + page.instance.toId + "/" +
            page.instance.distance, data)
            .then(res => {
                if (res.status === 200 && res.data.code === undefined) {
                    let message = "Route successfully added!";
                    dispatch(setMessage(message));
                    openNotification('success', message);
                    dispatch(needRefresh(true));
                } else {
                    dispatch(setMessage(res.data.message));
                    openNotification('error', res.data.message);
                }
                closeModal();
            })
            .catch(err => {
                dispatch(setMessage(err.response.data.message));
                openNotification('error', err.response.data.message);
            })
    }

    const handleCancel = () => {
        closeModal();
    };

    const handleReset = () => {
        dispatch(setInstance(initialInstance));
        form.resetFields();
    };

    const onFormLayoutChange = () => {
        // console.log(page.instance);
    };

    const openNotification = (type, message) => {
        notificationApi[type]({
            message: 'Notification',
            description: message,
        });
    };

    const onFinish = (values) => {
        console.log('Success:', values);
        switch (page.modal_type) {
            case ModalType.UPDATE:
                submitUpdate();
                break;
            case ModalType.ADD:
                submitAdd();
                break;
            case ModalType.BETWEEN:
                submitAddBetween();
                break;
            default:
                break;
        }
        closeModal();
    }

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

        return (
            <>
                {contextHolder}
                <Modal title={page.modal_type}
                       width={'50%'}
                       open={page.is_modal_open}
                       onOk={handleOk}
                       onCancel={handleCancel}
                       footer={[
                           <Button key='reset' htmlType='button' onClick={handleReset} disabled={page.modal_type === ModalType.SHOW}>Reset</Button>,
                           <Button key='back' htmlType='button' type='primary' onClick={handleCancel}>Cancel</Button>,
                           <Button key='submit' htmlType='submit' type='primary' onClick={handleOk}>OK</Button>
                       ]}
                >
                    <Form form={form} labelCol={{span: 4}} wrapperCol={{span: 14}} layout="horizontal"
                          onValuesChange={onFormLayoutChange}
                          onFinish={onFinish}
                          onFinishFailed={onFinishFailed}
                          autoComplete="off">
                        <Form.Item label='ID:' name='id' hidden={page.is_id_hidden} shouldUpdate={true}>
                            <InputNumber min={1}
                                         value={store.getState().page.instance.id}
                                         onValueChange={e => {
                                             dispatch(setInstance({...store.getState().page.instance, id: e}))
                                         }}
                                         disabled/>
                        </Form.Item>
                        <Form.Item label="Name:" name='name'
                                   rules={[{
                                               required: true,
                                               message: 'Please input name!',
                                           }]}>
                            <Input placeholder='Name' initialValue={store.getState().page.instance.name}
                                   value={store.getState().page.instance.name}
                                   onChange={e => {
                                       dispatch(setInstance({...store.getState().page.instance, name: e.target.value}))
                                   }}
                                   disabled={!store.getState().page.is_id_hidden}/>
                        </Form.Item>
                        <Form.Item label="Coordinates" required>
                            <Row>
                                <Space align='baseline'>
                                    <Form.Item name='x' rules={[{
                                        required: true,
                                        message: 'Please input X coordinate!',
                                    }]}>
                                        <InputNumber placeholder='X' min={1} value={store.getState().page.instance.x}
                                                     initialValue={store.getState().page.instance.x}
                                                     onChange={e => {
                                                         dispatch(setInstance({...store.getState().page.instance, x: e}))
                                                     }}
                                                     disabled={!store.getState().page.is_id_hidden}/>
                                    </Form.Item>
                                    <Form.Item name='y' rules={[{
                                        required: true,
                                        message: 'Please input Y coordinate!',
                                    }]}>
                                        <InputNumber placeholder='Y' value={store.getState().page.instance.y}
                                                     initialValue={store.getState().page.instance.y}
                                                     onChange={e => {
                                                         dispatch(setInstance({...store.getState().page.instance, y: e}))
                                                     }}
                                                     disabled={!store.getState().page.is_id_hidden}/>
                                    </Form.Item>
                                </Space>
                            </Row>
                        </Form.Item>
                        <Form.Item label="Creation Date" name='creationDate' hidden={store.getState().page.is_date_hidden}>
                            <Row>
                                <DatePicker
                                    format={dateFormat}
                                    value={dayjs(store.getState().page.instance.creationDate, dateFormat)}
                                    onChange={e => {
                                        dispatch(setInstance({...store.getState().page.instance, creationDate: dayjs(e).format(fullDateFormat)}))
                                    }}
                                    disabled={!store.getState().page.is_id_hidden} />
                            </Row>
                        </Form.Item>
                        <Form.Item label="From" required>
                            <Row>
                                <Space>
                                    <Form.Item name='fromId' hidden={page.modal_type !== ModalType.BETWEEN} rules={[{
                                        required: page.modal_type === ModalType.BETWEEN,
                                        message: 'Please input From ID!',
                                    }]}>
                                        <InputNumber placeholder='From ID' value={store.getState().page.instance.fromId}
                                                     initialValue={store.getState().page.instance.fromId}
                                                     onChange={e => {
                                                         dispatch(setInstance({...store.getState().page.instance, fromId: e}))
                                                     }}
                                                     disabled={!store.getState().page.is_id_hidden}/>
                                    </Form.Item>
                                    <Form.Item name='fromX' hidden={page.modal_type === ModalType.BETWEEN}
                                               rules={[{
                                        required: page.modal_type !== ModalType.BETWEEN,
                                        message: 'Please input From X coordinate!',
                                    }]}>
                                        <InputNumber placeholder='X' value={store.getState().page.instance.fromX}
                                                     initialValue={store.getState().page.instance.fromX}
                                                     onChange={e => {
                                                         dispatch(setInstance({...store.getState().page.instance, fromX: e}))
                                                     }}
                                                     disabled={!store.getState().page.is_id_hidden}/>
                                    </Form.Item>
                                    <Form.Item name='fromY' hidden={page.modal_type === ModalType.BETWEEN} rules={[{
                                        required: page.modal_type !== ModalType.BETWEEN,
                                        message: 'Please input From Y coordinate!',
                                    }]}>
                                        <InputNumber placeholder='Y' value={store.getState().page.instance.fromY}
                                                     initialValue={store.getState().page.instance.fromY}
                                                     onChange={e => {
                                                         dispatch(setInstance({...store.getState().page.instance, fromY: e}))
                                                     }}
                                                     disabled={!store.getState().page.is_id_hidden}/>
                                    </Form.Item>
                                    <Form.Item name='fromZ' hidden={page.modal_type === ModalType.BETWEEN} rules={[{
                                        required: page.modal_type !== ModalType.BETWEEN,
                                        message: 'Please input From Z coordinate!',
                                    }]}>
                                        <InputNumber placeholder='Z' value={store.getState().page.instance.fromZ}
                                                     initialValue={store.getState().page.instance.fromZ}
                                                     onChange={e => {
                                                         dispatch(setInstance({...store.getState().page.instance, fromZ: e}))
                                                     }}
                                                     disabled={!store.getState().page.is_id_hidden}/>
                                    </Form.Item>
                                </Space>
                            </Row>
                        </Form.Item>
                        <Form.Item label="To">
                            <Row>
                                <Form.Item name='ToId' hidden={page.modal_type !== ModalType.BETWEEN}>
                                    <InputNumber placeholder='From ID' value={store.getState().page.instance.toId}
                                                 initialValue={store.getState().page.instance.toId}
                                                 onChange={e => {
                                                     dispatch(setInstance({...store.getState().page.instance, toId: e}))
                                                 }}
                                                 disabled={!store.getState().page.is_id_hidden}/>
                                </Form.Item>
                                <Form.Item name='toName' hidden={page.modal_type === ModalType.BETWEEN}>
                                    <Input placeholder='Name' value={store.getState().page.instance.toName}
                                           initialValue={store.getState().page.instance.toName}
                                           onChange={e => {
                                               dispatch(setInstance({...store.getState().page.instance, toName: e.target.value}))
                                           }}
                                           disabled={!store.getState().page.is_id_hidden}/>
                                </Form.Item>
                            </Row>
                            <Row>
                                <Space>
                                    <Form.Item name='toX' hidden={page.modal_type === ModalType.BETWEEN}>
                                        <InputNumber placeholder='X' value={store.getState().page.instance.toX}
                                                     initialValue={store.getState().page.instance.toX}
                                                     onChange={e => {
                                                         dispatch(setInstance({...store.getState().page.instance, toX: e}))
                                                     }}
                                                     disabled={!store.getState().page.is_id_hidden}/>
                                    </Form.Item>
                                    <Form.Item name='toY' hidden={page.modal_type === ModalType.BETWEEN}>
                                        <InputNumber placeholder='Y' value={store.getState().page.instance.toY}
                                                     initialValue={store.getState().page.instance.toY}
                                                     onChange={e => {
                                                         dispatch(setInstance({...store.getState().page.instance, toY: e}))
                                                     }}
                                                     disabled={!store.getState().page.is_id_hidden}/>
                                    </Form.Item>
                                    <Form.Item name='toZ' hidden={page.modal_type === ModalType.BETWEEN}>
                                        <InputNumber placeholder='Z' value={store.getState().page.instance.toZ}
                                                     initialValue={store.getState().page.instance.toZ}
                                                     onChange={e => {
                                                         dispatch(setInstance({...store.getState().page.instance, toZ: e}))
                                                     }}
                                                     disabled={!store.getState().page.is_id_hidden}/>
                                    </Form.Item>
                                </Space>
                            </Row>
                        </Form.Item>
                        <Form.Item name='distance' label="Distance" rules={[{
                            required: true,
                            message: 'Please input distance!',
                        }]}>
                            <InputNumber min={2} placeholder='Distance' value={store.getState().page.instance.distance}
                                         initialValue={store.getState().page.instance.distance}
                                         onChange={e => {
                                             dispatch(setInstance({...store.getState().page.instance, distance: e}))
                                         }}
                                         disabled={!store.getState().page.is_id_hidden}/>
                        </Form.Item>
                    </Form>
                </Modal>
            </>
        );
    }