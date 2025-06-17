from flask import Blueprint, request, jsonify
from db import (
    get_compras_producto, get_compra_producto, 
    create_compra_producto, update_compra_producto, delete_compra_producto
)

compra_bp = Blueprint('compra_producto', __name__)

@compra_bp.route('', methods=['GET'])
def get_all():
    compras = get_compras_producto()
    return jsonify(compras), 200

@compra_bp.route('/<int:id_compra>', methods=['GET'])
def get_one(id_compra):
    compra = get_compra_producto(id_compra)
    if compra:
        return jsonify(compra), 200
    return jsonify({"error": "Compra producto not found"}), 404

@compra_bp.route('', methods=['POST'])
def create():
    data = request.json
    required = ['fecha_compra', 'id_producto', 'id_proveedor', 'cantidad_adquirida', 'precio_compra']
    
    if not all(key in data for key in required):
        return jsonify({"error": "Missing required fields"}), 400
    
    new_id = create_compra_producto(**{key: data[key] for key in required})
    return jsonify({"id_compra": new_id}), 201

@compra_bp.route('/<int:id_compra>', methods=['PUT'])
def update(id_compra):
    data = request.json
    if not data:
        return jsonify({"error": "No data provided"}), 400
    
    affected = update_compra_producto(id_compra, **data)
    if affected > 0:
        return jsonify({"message": "Compra producto updated"}), 200
    return jsonify({"error": "Compra producto not found"}), 404

@compra_bp.route('/<int:id_compra>', methods=['DELETE'])
def delete(id_compra):
    affected = delete_compra_producto(id_compra)
    if affected > 0:
        return jsonify({"message": "Compra producto deleted"}), 200
    return jsonify({"error": "Compra producto not found"}), 404