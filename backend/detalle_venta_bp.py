from flask import Blueprint, request, jsonify
from db import (
    get_detalles_venta, get_detalle_venta, 
    create_detalle_venta, update_detalle_venta, delete_detalle_venta
)

detalle_venta_bp = Blueprint('detalle_venta', __name__)

@detalle_venta_bp.route('', methods=['GET'])
def get_all():
    detalles = get_detalles_venta()
    return jsonify(detalles), 200

@detalle_venta_bp.route('/<int:id_detalle_venta>', methods=['GET'])
def get_one(id_detalle_venta):
    detalle = get_detalle_venta(id_detalle_venta)
    if detalle:
        return jsonify(detalle), 200
    return jsonify({"error": "Detalle venta not found"}), 404

@detalle_venta_bp.route('', methods=['POST'])
def create():
    data = request.json
    required = ['id_venta', 'id_producto', 'cantidad']
    
    if not all(key in data for key in required):
        return jsonify({"error": "Missing required fields"}), 400
    
    new_id = create_detalle_venta(
        id_venta=data['id_venta'],
        id_producto=data['id_producto'],
        cantidad=data['cantidad']
    )
    return jsonify({"id_detalle_venta": new_id}), 201

@detalle_venta_bp.route('/<int:id_detalle_venta>', methods=['PUT'])
def update(id_detalle_venta):
    data = request.json
    if not data:
        return jsonify({"error": "No data provided"}), 400
    
    affected = update_detalle_venta(id_detalle_venta, **data)
    if affected > 0:
        return jsonify({"message": "Detalle venta updated"}), 200
    return jsonify({"error": "Detalle venta not found"}), 404

@detalle_venta_bp.route('/<int:id_detalle_venta>', methods=['DELETE'])
def delete(id_detalle_venta):
    affected = delete_detalle_venta(id_detalle_venta)
    if affected > 0:
        return jsonify({"message": "Detalle venta deleted"}), 200
    return jsonify({"error": "Detalle venta not found"}), 404