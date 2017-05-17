/**
 * 提示框显示
 */
var layer=document.createElement("div");
layer.id="layer";
function layer_show(w,h,text,ts){
	var style={
				background: "#f7f3f3",
		        position: "fixed",
		        zIndex: 20,
		        width: w + "px",
		       // height: DIVheight + "px",
		        left: "50%",
		        top: "50%",
		        marginTop: -(h / 2) + "px",
		        marginLeft: -(w / 2) + "px",
		        opacity: "0.9",
		        borderRadius: "5px",
		        textAlign: "center",
		        color: "#f30303",
		        padding: "20px",
		        fontSize: "16px",
		        fontWeight: "600",
		        lineHeight: "18px"
	}
	for(var i in style){
		layer.style[i]=style[i];
	}
	layer.innerHTML=text;
	var str="document.body.removeChild(layer);";
	if (document.getElementById("layer")==null) {
		document.body.appendChild(layer);
		setTimeout(str, ts)
	}
}