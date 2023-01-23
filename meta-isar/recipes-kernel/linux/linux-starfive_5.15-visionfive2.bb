#
# Copyright (c) Siemens AG, 2023
#
# SPDX-License-Identifier: MIT

require recipes-kernel/linux/linux-custom.inc

SRC_URI += " \
    git://github.com/starfive-tech/linux.git;protocol=https;branch=JH7110_VisionFive2_devel;destsuffix=linux-visionfive-${PV} \
    file://fix-Error-unrecognized-opcode-csrr-a5-0xc01.patch \
    file://starfive2_extra.cfg"
SRCREV = "59cf9af678dbfa3d73f6cb86ed1ae7219da9f5c9"

S = "${WORKDIR}/linux-visionfive-${PV}"

KERNEL_DEFCONFIG = "starfive_visionfive2_defconfig"

LINUX_VERSION_EXTENSION = "-isar"
