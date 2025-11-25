from flask import Blueprint, request, jsonify
from db import (
    get_ventas, get_venta, create_venta, update_venta, delete_venta,
    get_ventas_efectivo, get_ventas_tarjeta
)

venta_bp = Blueprint('venta', __name__)

@venta_bp.route('', methods=['GET'])
def get_all():
    ventas = get_ventas()
    return jsonify(ventas), 200

@venta_bp.route('/<int:id_venta>', methods=['GET'])
def get_one(id_venta):
    venta = get_venta(id_venta)
    if venta:
        return jsonify(venta), 200
    return jsonify({"error": "Venta not found"}), 404

@venta_bp.route('', methods=['POST'])
def create():
    data = request.json
    if not data or 'fecha' not in data:
        return jsonify({"error": "Missing required data"}), 400
    
    new_id = create_venta(
        fecha=data['fecha'],
        id_cliente=data.get('id_cliente'),
        id_tipo_pago=data.get('id_tipo_pago')
    )
    return jsonify({"id_venta": new_id}), 201

@venta_bp.route('/<int:id_venta>', methods=['PUT'])
def update(id_venta):
    data = request.json
    if not data:
        return jsonify({"error": "No data provided"}), 400
    
    affected = update_venta(id_venta, **data)
    if affected > 0:
        return jsonify({"message": "Venta updated"}), 200
    return jsonify({"error": "Venta not found"}), 404

@venta_bp.route('/<int:id_venta>', methods=['DELETE'])
def delete(id_venta):
    affected = delete_venta(id_venta)
    if affected > 0:
        return jsonify({"message": "Venta deleted"}), 200
    return jsonify({"error": "Venta not found"}), 404

# Payment type fragments
@venta_bp.route('/efectivo', methods=['GET'])
def get_efectivo():
    ventas = get_ventas_efectivo()
    return jsonify(ventas), 200

@venta_bp.route('/tarjeta', methods=['GET'])
def get_tarjeta():
    ventas = get_ventas_tarjeta()
    return jsonify(ventas), 200