
// Get data
const proSelects = $('#province');
const disSelects = $('#district');
const wardSelects = $('#ward');
const addressInput = $('input[name="address"]');

let provinceCode = $('#provinceCode').val();
let districtCode = $('#districtCode').val();
let wardCode = $('#wardCode').val();

const orderBtn = $('.btn__order');
const baseUrl = "https://provinces.open-api.vn/api";


// get url
function handleGetBaseUrl() {
	const { hostname, host, protocol } = window.location;
	let url = "";
	if (hostname !== 'localhost') {
		url = `${protocol}//${host}`;
	} else {
		url = `${protocol}//${host}/shoplane-ft`;
	}
	return url;
}


function createOptionList(list, val) {
	return list.map(item => createOption(item.code, item.name, val)).join("");
}

function createOption(value, text, val) {
	return `<option value="${value}"${+val === value ? 'selected' : ''}>${text}</option>`;
}


function handlegetWardByDisCode(disCode) {
	$.ajax({
		url: `${baseUrl}/d/${disCode}?depth=2`,
		type: "GET",
		success: function(res) {
			let html = "";
			if (res.wards) {
				html = `<option value="-1">Vui lòng chọn phường (xã)...</option>`
					.concat(createOptionList(res.wards, wardCode));
			}
			wardSelects.empty();
			wardSelects.append(html);
		}
	})

}

// username = ...., pwd = ....
function handleGetDisByProCode(proCode) {
	console.log(proCode);
	$.ajax({
		url: `${baseUrl}/p/${proCode}?depth=2`,
		type: "GET",
		success: function(res) {
			let html = "";
			if (res.districts) {
				html = `<option value="-1">Vui lòng chọn quận (huyện)...</option>`
					.concat(createOptionList(res.districts, districtCode));
			}
			disSelects.empty();
			disSelects.append(html);
			wardSelects.empty();
			wardSelects.append(`<option value="-1">Vui lòng chọn phường (xã)...</option>`);
			if (+districtCode !== -1) {
				handlegetWardByDisCode(districtCode);
			} else {
				console.log("no");
			}
		}
	})
}
function handleGetAllPros() {
	$.ajax({
		url: `${baseUrl}/p`,
		type: 'GET',
		dataType: 'json', // added data type
		success: function(res) {
			let html = ""
			if (res) {
				html = createOptionList(res, provinceCode);
			}
			proSelects.append(html);
			if (+provinceCode !== -1) {
				handleGetDisByProCode(provinceCode);
			}
		},
	})
}

function handleProSelectChange() {
	proSelects.change(() => {
		provinceCode = $("#province option:selected").val();
		this.handleGetDisByProCode(provinceCode);
		// chagne address if user select 
		const province = $("#province option:selected").text().trim();
		const address = `${province}`;
		addressInput.val(address);
	});
}

function handleDisSelectChange() {
	disSelects.change(() => {
		districtCode = $("#district option:selected").val();
		this.handlegetWardByDisCode(districtCode);
		const extraAddress = $('input[name="address"]').val().trim();
		const district = $("#district option:selected").text().trim();
		const address = `${extraAddress}, ${district}`;
		addressInput.val(address);
	});
}

function handleWardSelectChange() {
	wardSelects.change(() => {
		wardCode = $("#ward option:selected").val();
		const extraAddress = $('input[name="address"]').val().trim();
		const ward = $("#ward option:selected").text().trim();
		const address = `${extraAddress}, ${ward}`;
		addressInput.val(address);
	});
}

function handleSubmitOrder() {
	orderBtn.click(() => {
		// get data
		const totalPrice = $('input[name="totalPrice"]').val().trim();
		const fullname = $('input[name="fullname"]').val().trim();
		const phonenumber = $('input[name="phonenumber"]').val().trim();
		const extraAddress = $('input[name="address"]').val().trim();
		const address = `${extraAddress}`;

		// Validate
		if (fullname === "") {
			swal("Họ tên không được để trống");
			return;
		}
		if (phonenumber === "") {
			swal("Số điện thoại không được để trống");
			return;
		}
		if (provinceCode === '-1') {
			swal("Vui lòng chọn thành phố");
			return;
		}
		if (districtCode === '-1') {
			swal("Vui lòng chọn huyện");
			return;
		}
		if (wardCode === '-1') {
			swal("Vui lòng chọn xã");
			return;
		}
		// Create data
		const data = {
			fullname,
			phonenumber,
			address,
			totalPrice,
			provinceCode,
			districtCode,
			wardCode
		}
		const baseUrl = handleGetBaseUrl();
		// Post data
		request = $.ajax({
			url: `${baseUrl}/checkout`,
			type: "POST",
			data: data,
			success: function(res) {
				if (res === 'success') {
					swal("Đặt hàng thành công", "Vui lòng kiểm tra email để xem chi tiết đơn hàng", "success")
						.then(() => {
							window.location.reload()
						});
				} else {
					swal("Đặt hàng thất bại", "Đã có lỗi xảy ra xin vui lòng đặt hàng lại", "info")
						.then(() => {
							window.location.reload()
						});
				}
			}
		});

	})
}

function main() {
	handleGetAllPros();
	handleProSelectChange();
	handleDisSelectChange();
	handleWardSelectChange();

	// Handle submit
	handleSubmitOrder();
}

main();
