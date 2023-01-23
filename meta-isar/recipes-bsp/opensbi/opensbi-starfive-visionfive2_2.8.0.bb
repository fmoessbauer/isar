#
# Copyright (c) Siemens AG, 2023
#
# SPDX-License-Identifier: MIT

inherit dpkg

DESCRIPTION = "OpenSBI firmware for StarFive VisionFive 2"

SRC_URI = " \
    git://github.com/starfive-tech/opensbi.git;destsuffix=opensbi-${PV};branch=JH7110_VisionFive2_devel \
    file://starfive-visionfive2-rules.tmpl"
SRCREV = "7700244f4d334d765ee5d994c3849ade09fb6844"

S = "${WORKDIR}/opensbi-${PV}"
TEMPLATE_FILES += "starfive-visionfive2-rules.tmpl"
TEMPLATE_VARS += "DTB_UBOOT_JH7110_VF2"

DEPENDS = "u-boot-starfive-visionfive2"
DEBIAN_BUILD_DEPENDS = " \
    u-boot-starfive-visionfive2, \
    u-boot-starfive-visionfive2-dev"

do_prepare_build[cleandirs] += "${S}/debian"
do_prepare_build() {
    cp ${WORKDIR}/starfive-visionfive2-rules ${WORKDIR}/rules
    deb_debianize

    echo "build/platform/generic/firmware/fw_payload.bin /usr/lib/opensbi/starfive-visionfive2/" > ${S}/debian/install
}
