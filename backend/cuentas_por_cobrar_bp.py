from flask import Blueprint, request, jsonify
from db import (
    get_cuentas_por_cobrar, get_cuenta_por_cobrar, 
    create_cuenta_por_cobrar, update_cuenta_por_cobrar, delete_cuenta_por_cobrar
)

cuenta_bp = Blueprint('cuenta_por_cobrar', __name__)

@cuenta_bp.route('', methods=['GET'])
def get_all():
    cuentas = get_cuentas_por_cobrar()
    return jsonify(cuentas), 200

@cuenta_bp.route('/<int:id_cuenta>', methods=['GET'])
def get_one(id_cuenta):
    cuenta = get_cuenta_por_cobrar(id_cuenta)
    if cuenta:
        return jsonify(cuenta), 200
    return jsonify({"error": "Cuenta por cobrar not found"}), 404

@cuenta_bp.route('', methods=['POST'])
def create():
    data = request.json
    required = ['id_venta', 'fecha_inicio']
    
    if not all(key in data for key in required):
        return jsonify({"error": "Missing required fields"}), 400
    
    new_id = create_cuenta_por_cobrar(
        id_venta=data['id_venta'],
        fecha_inicio=data['fecha_inicio'],
        estado=data.get('estado', 'Pendiente')
    )
    return jsonify({"id_cuenta": new_id}), 201

@cuenta_bp.route('/<int:id_cuenta>', methods=['PUT'])
def update(id_cuenta):
    data = request.json
    if not data or 'estado' not in data:
        return jsonify({"error": "Estado is required"}), 400
    
    affected = update_cuenta_por_cobrar(id_cuenta, data['estado'])
    if affected > 0:
        return jsonify({"message": "Cuenta por cobrar updated"}), 200
    return jsonify({"error": "Cuenta por cobrar not found"}), 404

@cuenta_bp.route('/<int:id_cuenta>', methods=['DELETE'])
def delete(id_cuenta):
    affected = delete_cuenta_por_cobrar(id_cuenta)
    if affected > 0:
        return jsonify({"message": "Cuenta por cobrar deleted"}), 200
    return jsonify({"error": "Cuenta por cobrar not found"}), 404