#
# Copyright (c) Siemens AG, 2023
#
# SPDX-License-Identifier: MIT

inherit dpkg

DESCRIPTION = "OpenSBI firmware for StarFive VisionFive 2"

SRC_URI = " \
    git://github.com/riscv-software-src/opensbi.git;destsuffix=opensbi-${PV};protocol=https;branch=master \
    file://starfive-visionfive2-rules.tmpl"
# required patches are not yet part of a release, but will be in 1.3
SRCREV = "65c2190b47b14341dac9f763fed2d4ecbbff2a69"

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
