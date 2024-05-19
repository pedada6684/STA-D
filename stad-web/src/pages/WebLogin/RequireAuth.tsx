import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

export default function RequireAuth(props: any) {
  const isLoggedIn = useSelector((state: RootState) => state.user.isLoggedIn);
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLoggedIn) {
      navigate("/web-login");
    }
  }, [navigate]);
  return props.children;
}
