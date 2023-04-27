import { Link } from 'react-router-dom'
import logo from '../images/logo.png'
import './Login.css';


export default function Signup() {
    return (
        <div className="floating-card">
            <div className="card-body p-md-5 mx-md-4">

                <div className="icon-picture">
                    <img src={logo} />
                </div>

                <div className="text-center">
                    <h2 className="mt-1 mb-5 pb-1">فرم ثبت نام</h2>
                </div>

                <form>
                    <p><strong>لطفا اطلاعات خود را وارد کنید</strong></p>

                    <div className="form-outline mb-4">
                        <input type="email" id="form2Example11" className="form-control" />
                        <label className="form-label" for="form2Example11">نام کاربری</label>
                    </div>

                    <div className="form-outline mb-4">
                        <input type="password" id="form2Example22" className="form-control" />
                        <label className="form-label" for="form2Example22">رمز عبور</label>
                    </div>

                    <div className="form-outline mb-4">
                        <input type="password" id="form2Example22" className="form-control" />
                        <label className="form-label" for="form2Example22">تکرار رمز عبور</label>
                    </div>

                    <div className="text-center pt-1 mb-5 pb-1">
                        <button className="btn btn-danger btn-block fa-lg gradient-custom-2 mb-3" type="button">ثبت نام</button>
                    </div>

                    <div className="d-flex align-items-center justify-content-center pb-4">
                        <div className="mx-3">
                            <Link to='/login'>
                                <button type="button" className="btn btn-outline-danger">ورود</button>
                            </Link>
                        </div>
                        <p className="mb-0 me-2">حساب کاربری دارید؟</p>
                    </div>

                </form>

            </div>
        </div>

    )
}